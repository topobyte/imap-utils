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

package de.topobyte.imaputils;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Store;

import com.google.common.base.Strings;

import de.topobyte.adt.tree.TreeUtil;
import de.topobyte.adt.tree.Visitor;
import de.topobyte.adt.tree.visitors.stdio.StdPrintVisitor;
import de.topobyte.adt.trees.general.sorted.Node;
import de.topobyte.adt.trees.general.sorted.SortedTree;

public class FolderLister
{

	private Store store;
	private SortedTree<String> tree = new SortedTree<>();

	public FolderLister(Store store)
	{
		this.store = store;
	}

	public void execute() throws MessagingException
	{
		Folder mainFolder = store.getDefaultFolder();
		Node<String> node = tree.getRoot();
		list(mainFolder, 0, node);

		Visitor<String> visitor = new StdPrintVisitor<>(false);
		System.out.println("TREE:");
		TreeUtil.traversePreorder(tree, visitor);
	}

	private void list(Folder folder, int depth, Node<String> node)
			throws MessagingException
	{
		Folder[] folders = folder.list();
		for (Folder child : folders) {
			String folderName = child.getName();
			System.out.println(String.format("%s%s", Strings.repeat(" ", depth),
					folderName));
			Node<String> childNode = node.add(folderName);
			list(child, depth + 1, childNode);
		}
	}

	public SortedTree<String> getTree()
	{
		return tree;
	}

}