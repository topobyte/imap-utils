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

import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;
import com.sun.mail.imap.IMAPFolder;

import de.topobyte.guava.util.MultisetUtil;
import de.topobyte.guava.util.Order;

public class ListIdFinder extends StopConditionFolderProcessor
{

	private Multiset<String> listIds = HashMultiset.create();

	public ListIdFinder(IMAPFolder folder, MessageStopCondition stopCondition)
	{
		super(folder, stopCondition);
	}

	@Override
	public void execute() throws MessagingException
	{
		super.execute();

		List<Entry<String>> entries = MultisetUtil.entries(listIds,
				Order.ASCENDING, Order.DESCENDING);

		for (Entry<String> entry : entries) {
			System.out.println(String.format("%d: %s", entry.getCount(),
					entry.getElement()));
		}
	}

	@Override
	public void process(Message msg) throws MessagingException
	{
		String[] listIds = msg.getHeader("List-Id");
		if (listIds != null) {
			String listId = listIds[0];
			this.listIds.add(listId);
		}
	}

}
