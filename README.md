# License

This library is released under the terms of the GNU Lesser General Public
License.

See [LGPL.md](LGPL.md) and [GPL.md](GPL.md) for details.

# Setup

Run `./gradlew clean create` to make the scripts work.

# Tests

In order to be able to run the tests on an IMAP account, add a configuration
such as this at location `core/src/test/resources/config`:

    host=your.host.name.com
    user=your.user
