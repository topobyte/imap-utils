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

import java.util.Arrays;
import java.util.List;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.SortTerm;

public abstract class FolderProcessor
{

	final static Logger logger = LoggerFactory.getLogger(FolderProcessor.class);

	private boolean printInfo;
	private IMAPFolder folder;

	public FolderProcessor(IMAPFolder folder, boolean printInfo)
	{
		this.printInfo = printInfo;
		this.folder = folder;
	}

	public abstract boolean done(Message msg);

	public abstract void process(Message msg) throws MessagingException;

	public void execute() throws MessagingException
	{
		execute(null);
	}

	public void execute(SortTerm[] sortTerms) throws MessagingException
	{
		List<Message> messages = sortTerms == null
				? Arrays.asList(folder.getMessages())
				: Arrays.asList(folder.getSortedMessages(sortTerms));

		int i = 0;
		for (Message msg : Lists.reverse(messages)) {
			if (done(msg)) {
				if (printInfo) {
					System.out.println("Stopping processing. Next message:");
					printInfo(i++, msg);
				}
				break;
			}

			if (printInfo) {
				printInfo(i++, msg);
			}

			process(msg);
		}
	}

	private void printInfo(int i, Message msg) throws MessagingException
	{
		logger.info(Strings.repeat("*", 72));
		logger.info(String.format("MESSAGE %d:", (i++ + 1)));

		long id = folder.getUID(msg);
		logger.info("ID: " + id);

		Address[] recipients = msg.getAllRecipients();

		logger.info("Subject: " + msg.getSubject());
		logger.info("From: " + getFirstSafe(msg.getFrom()));
		logger.info("Reply to: " + getFirstSafe(msg.getReplyTo()));
		logger.info("To: " + getFirstSafe(recipients));
		logger.info("Date: " + msg.getReceivedDate());
		logger.info("Size: " + msg.getSize());
		logger.info("Flags: " + msg.getFlags());
	}

	private <T> T getFirstSafe(T[] array)
	{
		if (array == null || array.length == 0) {
			return null;
		}
		return array[0];
	}

}
