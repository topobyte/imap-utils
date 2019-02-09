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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Splitter;

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

		List<String> lines = IOUtils.readLines(input);
		input.close();

		Map<String, String> map = new HashMap<>();

		Splitter colonSplitter = Splitter.on(":").limit(2);
		for (String line : lines) {
			List<String> parts = colonSplitter.splitToList(line);
			if (parts.size() != 2) {
				continue;
			}
			String key = parts.get(0);
			String value = parts.get(1);
			map.put(key, value);
		}

		String host = map.get("host");
		String username = map.get("user");

		return new Config(host, username);
	}

}
