# MineChat 

A Bukkit/Spigot plugin for text-based communication in Minecraft.

This plugin is a hobby/side-project, and does not receive as much attention as it deserves. 
As such, it is released open-source for anyone who wishes to extend to it through forks, and
possibly contribute back with pull-requests.

## Features

### End-user

- [x] Private messaging with easy replies (`\pm` & `\r` respectively)
- [x] Server-broadcast command (`\say`)
- [x] Range-based communication with players
  - [x] Overriding range with a message format (Starting message with `!`)
- [x] Color and text formatting for users
- [ ] Speed-dial functionality for quick communication with friends (in-progress)
- [ ] Communication with offline players, through 'voicemail'-like feature (in-progress)
- [x] Reload command for hot-reloading configuration (`\minechat reload`)

### Administrative

- [x] Configurable message formats for all communication types
  - [x] Integration into `Vault` plugin to extract prefix/suffix for usernames (usable in message formats)
  - [ ] Integration with PEX (is this even supported anymore?)
  - [ ] Local-overridable user prefix/suffix system if no plugin exists (in-progress)
- [x] Granular permissions with reasonable defaults for most functionality

## How to Build

This project is set up using `gradle`, which makes building as simple as running the command:
```sh
./gradlew build # gradlew.bat build from windows
```
at the root of the repo. 

The built product will be found under `build/libs/MineChat-<version>.jar`

## How to Use / Setup

1. Place the plugin's `.jar` file in your server's `plugins/` directory
2. Launch the server. If this is the first time the plugin has been used with the server, this will generate a configuration file under `plugins/MineChat/config.yml`
3. Adjust any desired configuration
4. Reload configuration with `\minechat reload`, or through restarting the server

## Contributing

This project is open-source and designed as a side/hobby-project of the creator. Please feel free 
to fork this project and/or contribute back with pull requests!

For more information, see the [`CONTRIBUTING.md`](.github/CONTRIBUTING.md) file and the [`CODE_OF_CONDUCT.md`](CODE_OF_CONDUCT.md).

## <a name="license"></a>License

<img align="right" src="http://opensource.org/trademarks/opensource/OSI-Approved-License-100x137.png">

The class is licensed under the [MIT License](http://opensource.org/licenses/MIT):

Copyright &copy; 2018 Matthew Rodusek

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.