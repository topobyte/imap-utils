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

package de.topobyte.imaputils.processors;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.mail.Message;
import javax.mail.MessagingException;

public class MaxDaysStopCondition implements MessageStopCondition
{

	private int maxDays;

	public MaxDaysStopCondition(int maxDays)
	{
		this.maxDays = maxDays;
	}

	@Override
	public boolean done(Message msg)
	{
		try {
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime receive = LocalDateTime.ofInstant(
					msg.getReceivedDate().toInstant(), ZoneId.systemDefault());
			Duration duration = Duration.between(now, receive);
			long days = duration.toDays();
			return days < -maxDays;
		} catch (MessagingException e) {
			return false;
		}
	}

}
