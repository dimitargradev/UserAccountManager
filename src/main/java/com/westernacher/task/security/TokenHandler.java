package com.westernacher.task.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.westernacher.task.cache.CacheManager;
import com.westernacher.task.model.User;
import com.westernacher.task.util.JsonUtil;

public final class TokenHandler {

	private static final String HMAC_ALGO = "HmacSHA256";
	private static final String SEPARATOR = ".";
	private static final String SEPARATOR_SPLITTER = "\\.";

	private final Mac hmac;

	public TokenHandler(byte[] secretKey) {
		try {
			hmac = Mac.getInstance(HMAC_ALGO);
			hmac.init(new SecretKeySpec(secretKey, HMAC_ALGO));
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			throw new IllegalStateException("failed to initialize HMAC: " + e.getMessage(), e);
		}
	}

	/**
	 * Checks the token.
	 *
	 * @param token
	 * @return com.scalefocus.model.User
	 */
	public User parseUserFromToken(String token) {
		// Checks for null token
		if (token != null) {
			final String[] parts = token.split(SEPARATOR_SPLITTER);

			// Checks for correct format
			if ((parts.length == 2) && (parts[0].length() > 0) && (parts[1].length() > 0)) {
				try {
					final byte[] userBytes = fromBase64(parts[0]);
					final byte[] hash = fromBase64(parts[1]);

					// Checks if the signing hash is valid
					final boolean validHash = Arrays.equals(createHmac(userBytes), hash);
					if (validHash) {
						// Parses the User from the userBytes array
						final User user = JsonUtil.fromJsonString(userBytes, User.class);

						// Checks if is in cache and if it is expired and if the
						// given token is the same as the one
						// stored in the cache
						final User cachedUser = (User) CacheManager.getCache(user.getUserId());
						if ((cachedUser != null) && (cachedUser.getToken() != null)
								&& cachedUser.getToken().equals(token)) {
							return user;
						}
					}
				} catch (final IllegalArgumentException e) {
					// log tempering attempt here
				}
			}
		}
		return null;
	}

	public String createTokenForUser(User user) {

		final byte[] userBytes = JsonUtil.toJsonByteArray(createUserProjection(user));
		final byte[] hash = createHmac(userBytes);
		final StringBuilder sb = new StringBuilder(170);
		sb.append(toBase64(userBytes));
		sb.append(SEPARATOR);
		sb.append(toBase64(hash));
		return sb.toString();
	}

	private User createUserProjection(User user) {
		final User projection = new User();
		projection.setUserId(user.getUserId());
		projection.setName(user.getName());
		projection.setUsername(user.getUsername());
		projection.setPassword(user.getPassword());
		return projection;
	}

	private String toBase64(byte[] content) {
		return DatatypeConverter.printBase64Binary(content);
	}

	private byte[] fromBase64(String content) {
		return DatatypeConverter.parseBase64Binary(content);
	}

	// synchronized to guard internal hmac object
	private synchronized byte[] createHmac(byte[] content) {
		return hmac.doFinal(content);
	}
}
