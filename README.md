# UxPlay Embedded Linux Image for Raspberry Pi 4

This project is a custom embedded Linux image for the Raspberry Pi 4 that includes [UxPlay](https://github.com/FDH2/UxPlay), an open-source AirPlay mirroring and audio-streaming receiver.

The image was created using the Yocto Project and is intended to allow an iPhone or other compatible Apple device to mirror its screen to a Raspberry Pi 4.

## Project Information

* Target board: Raspberry Pi 4
* Yocto release: Kirkstone
* Machine configuration: `raspberrypi4-64`
* Architecture: AArch64 / 64-bit ARM
* Image recipe: `amit-uxplay-image`
* AirPlay receiver: UxPlay
* UxPlay version used: `1.73.6`

## Repository Contents

This repository contains the files added or modified for the project.

```text
.
├── conf/
│   ├── bblayers.conf
│   └── local.conf
│
└── meta-amit/
    ├── conf/
    ├── recipes-core/
    ├── recipes-images/
    └── recipes-multimedia/
```

### `conf`

The `conf` directory contains the Yocto build configuration files:

* `local.conf`: Defines the Raspberry Pi machine, image options and build configuration.
* `bblayers.conf`: Defines the Yocto layers used by the project.

### `meta-amit`

`meta-amit` is the custom Yocto layer created for this project.

It contains:

* The custom embedded Linux image recipe.
* The UxPlay BitBake recipe.
* UxPlay build dependencies.
* UxPlay runtime configuration.
* Any required system services or image customizations.

## Required Yocto Layers

The project was built using the following layers:

```text
poky/meta
poky/meta-poky
poky/meta-yocto-bsp
meta-openembedded/meta-oe
meta-openembedded/meta-multimedia
meta-openembedded/meta-networking
meta-raspberrypi
meta-amit
```

All layers should use branches compatible with Yocto Kirkstone.

## Build Instructions

First, clone Poky and the required layers:

```bash
git clone -b kirkstone https://git.yoctoproject.org/poky
cd poky

git clone -b kirkstone https://github.com/agherzan/meta-raspberrypi.git
git clone -b kirkstone https://github.com/openembedded/meta-openembedded.git
```

Copy the `meta-amit` layer into the Poky directory:

```bash
cp -r /path/to/meta-amit .
```

Initialize the Yocto build environment:

```bash
source oe-init-build-env build
```

Copy the configuration files from this repository:

```bash
cp /path/to/repository/conf/local.conf conf/local.conf
cp /path/to/repository/conf/bblayers.conf conf/bblayers.conf
```

Build the custom image:

```bash
bitbake amit-uxplay-image
```

After a successful build, the generated image can be found in:

```text
build/tmp/deploy/images/raspberrypi4-64/
```

The main SD-card image will normally have a name similar to:

```text
amit-uxplay-image-raspberrypi4-64.wic.bz2
```

## Flashing the Image

The compressed image can be flashed to a microSD card using:

* Raspberry Pi Imager
* Balena Etcher
* `bmaptool`
* `dd`

Example using `bmaptool`:

```bash
sudo bmaptool copy \
  amit-uxplay-image-raspberrypi4-64.wic.bz2 \
  /dev/sdX
```

Replace `/dev/sdX` with the correct microSD-card device.

Be careful when selecting the destination device because flashing the image will erase its existing contents.

## Running UxPlay

Boot the Raspberry Pi using the flashed microSD card and connect it to the same network as the iPhone.

UxPlay can be started with:

```bash
UxPlay
```

Depending on the image configuration, UxPlay may also start automatically as a system service.

To check whether the service is running:

```bash
systemctl status uxplay
```

To start it manually:

```bash
systemctl start uxplay
```

On the iPhone:

1. Open Control Center.
2. Select **Screen Mirroring**.
3. Select the Raspberry Pi or UxPlay receiver.
4. The iPhone should connect to the Raspberry Pi through AirPlay.

## Current Project Status

The custom Yocto image builds successfully for the Raspberry Pi 4 and includes UxPlay.

The following functionality has been tested:

* Raspberry Pi 4 boot.
* Ethernet network connection.
* UxPlay startup.
* AirPlay device discovery.
* Connection from an iPhone.
* Screen mirroring through UxPlay.

## Notes

* The Raspberry Pi and iPhone must be connected to the same network.
* Avahi is required for AirPlay service discovery.
* GStreamer is used by UxPlay for audio and video playback.
* Display behavior depends on the graphics and GStreamer configuration included in the image.
* This repository contains the custom Yocto configuration and layer, not the complete Yocto build output.

## Upstream Project

UxPlay is developed and maintained in the following repository:

https://github.com/FDH2/UxPlay

UxPlay is distributed under the GNU General Public License version 3 or later.

## License

The custom files in this repository should be distributed under a license selected by the repository owner.

Third-party components, including UxPlay, Yocto Project components, GStreamer and other packages, remain subject to their respective licenses.
