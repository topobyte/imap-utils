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
