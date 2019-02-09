# License

This library is released under the terms of the GNU Lesser General Public
License.

See [LGPL.md](LGPL.md) and [GPL.md](GPL.md) for details.

# Setup

Run `./gradlew clean create` to make the scripts work.

# CLI / Tests configuration

To configure the IMAP account the command line tools work with, add a
configuration file at location `cli/src/main/resources/config`.

In order to be able to run the tests on an IMAP account, add a configuration
file at location `core/src/test/resources/config`.

Example configuration file:

    host=your.host.name.com
    user=your.user

# CLI

Run the command line interface like this:

    ./scripts/imap-utils <command>

where <command> may be one of the following:

    list-folders
    list-list-ids

* `list-folders` will list the folder tree of your mailbox.
* `list-list-ids` scans all mails in the INBOX folder, looks for the
  List-Id header field, and will print a histogram of the values found.
