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
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.google.common.base.Splitter;

public class Config
{

	public static Config read(InputStream input) throws IOException
	{
		List<String> lines = IOUtils.readLines(input, StandardCharsets.UTF_8);
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

	private String host;
	private String username;
	private String password;

	public Config(String host, String username)
	{
		this.host = host;
		this.username = username;
	}

	public String getHost()
	{
		return host;
	}

	public void setHost(String host)
	{
		this.host = host;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

}
