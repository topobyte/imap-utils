// Copyright 2019 Sebastian Kuerten
//
// This file is part of imap-utils.
//
// imap-utils is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// imap-utils is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with imap-utils. If not, see <http://www.gnu.org/licenses/>.

package de.topobyte.imaputils.config;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.topobyte.imaputils.Password;

public class ConfigUtil
{

	final static Logger logger = LoggerFactory.getLogger(ConfigUtil.class);

	public static Config read() throws IOException
	{
		String resource = "config";
		InputStream input = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(resource);

		if (input == null) {
			String resourcesPath = "core/src/test/resources/";
			logger.error("Configuration not found on classpath");
			logger.error(String.format("Please create file '%s'",
					resourcesPath + resource));
			logger.error("More information is available in the README.md file");
			return null;
		}

		return Config.read(input);
	}

	public static Config obtainConfig()
	{
		Config config = null;
		try {
			config = read();
		} catch (IOException e) {
			// ignore
		}
		if (config == null) {
			return null;
		}
		Password.Result result = Password.get();
		if (!result.isValid()) {
			return null;
		}
		config.setPassword(new String(result.getPassword()));
		return config;
	}

}
