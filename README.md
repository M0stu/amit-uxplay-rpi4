# AMIT UxPlay Raspberry Pi 4 — Yocto Embedded Linux Project

A custom Embedded Linux project based on the **Yocto Project / OpenEmbedded** for the **Raspberry Pi 4 (64-bit)**.

The project was developed as part of the AMIT / Bullet embedded Linux project and demonstrates the integration of networking, multimedia, graphical applications, SOME/IP middleware, observability, and custom applications into two separate Linux distributions.

## Project Overview

The project targets:

**Hardware:** Raspberry Pi 4
**Architecture:** AArch64 / ARM64
**Yocto Release:** Kirkstone 4.0.x
**Target Machine:** `raspberrypi4-64`
**Linux Kernel:** 5.15.x

> Repository: `M0stu/amit-uxplay-rpi4`
>
> Custom Yocto/OpenEmbedded project targeting Raspberry Pi 4 with
> UxPlay, vSomeIP, Qt5, systemd/SysVinit distributions, networking,
> audio, SSH, Wi-Fi, Nano, a native Hello Bullet application, and a
> custom observability layer.

The tested development environment used:

```text
Yocto / Poky: 4.0.35 (Kirkstone)
BitBake: 2.0.0
Kernel: linux-raspberrypi 5.15.92
Target: aarch64-poky-linux
Tune: Cortex-A72
```

## Main Features

The project integrates the following functionality:

| Feature                 | Implementation         |
| ----------------------- | ---------------------- |
| Target Hardware         | Raspberry Pi 4 64-bit  |
| Linux Kernel            | 5.15.92                |
| Remote Access           | OpenSSH                |
| Wi-Fi                   | WPA Supplicant         |
| Text Editor             | Nano 6.2               |
| Audio                   | ALSA                   |
| Service Discovery       | Avahi                  |
| Multimedia Framework    | GStreamer              |
| Screen Mirroring        | UxPlay 1.73.6          |
| SOME/IP Middleware      | COVESA vSomeIP 3.4.10  |
| GUI Framework           | Qt 5.15                |
| Native Test Application | Hello Bullet           |
| Observability           | Custom Bullet Observer |
| Init Systems            | systemd and SysVinit   |

---

# Repository Layers

The repository contains two custom Yocto layers.

## `meta-amit`

The primary AMIT project layer.

It contains:

```text
meta-amit/
├── conf/
│   ├── layer.conf
│   └── distro/
│       ├── amit-systemd.conf
│       └── amit-sysvinit.conf
│
├── recipes-connectivity/
│   └── vsomeip/
│
├── recipes-core/
│   ├── hello-bullet/
│   ├── images/
│   └── packagegroups/
│
└── recipes-multimedia/
    └── uxplay/
```

The layer provides the custom images, vSomeIP integration, UxPlay, package groups, native Hello Bullet application, and both custom distribution definitions.

## `meta-observability`

A separate custom layer implementing lightweight embedded-system monitoring.

It contains:

```text
meta-observability/
├── conf/
├── recipes-core/
└── recipes-observability/
    └── bullet-observer/
```

The observer records useful system information including:

```text
System uptime
Load average
Memory usage
Root filesystem usage
Network interface statistics
Timestamped monitoring information
```

Monitoring information is written on the target to:

```text
/var/log/bullet-observability.log
```

The observer supports both:

```text
systemd
SysVinit
```

---

# Custom Distributions

The project provides two different distributions.

## Distribution 1 — AMIT Systemd

Distribution:

```text
amit-systemd
```

Image:

```text
amit-systemd-image
```

Init system:

```text
systemd
```

This distribution includes Qt5 and is intended for applications that require a graphical framework.

Its major components are:

```text
Qt5
systemd
OpenSSH
Wi-Fi
Nano
ALSA
Avahi
GStreamer
UxPlay
vSomeIP
Hello Bullet
Bullet Observer
```

## Distribution 2 — AMIT SysVinit

Distribution:

```text
amit-sysvinit
```

Image:

```text
amit-sysvinit-image
```

Init system:

```text
SysVinit
```

This distribution intentionally excludes the `meta-qt5` layer and Qt runtime.

Its major components are:

```text
SysVinit
OpenSSH
Wi-Fi
Nano
ALSA
Avahi
GStreamer
UxPlay
vSomeIP
Hello Bullet
Bullet Observer
```

This allows the project to demonstrate the difference between a feature-rich graphical systemd distribution and a smaller non-Qt SysVinit distribution.

---

# Package Groups

The project uses package groups instead of placing all dependencies directly inside the image recipes.

## `packagegroup-amit-base`

Provides common functionality:

```text
hello-bullet
nano
openssh
wpa-supplicant
alsa-utils
avahi-daemon
avahi-utils
```

## `packagegroup-amit-middleware`

Provides middleware:

```text
vsomeip
```

## `packagegroup-amit-qt5`

Provides Qt5 components used by the systemd distribution:

```text
qtbase
qtbase-plugins
qtbase-tools
```

## `packagegroup-amit-observability`

Provides:

```text
bullet-observer
```

---

# Hello Bullet Application

`hello-bullet` is a small native C application used to verify the custom recipe and Yocto cross-compilation environment.

The program is cross-compiled for AArch64 and installed as:

```text
/usr/bin/hello-bullet
```

Example output:

```text
========================================
 Hello Bullet!
 AMIT Embedded Linux Project
 Target: Raspberry Pi 4
========================================
```

The generated executable was verified as an ARM64 binary:

```text
ELF 64-bit LSB pie executable, ARM aarch64
```

---

# UxPlay

The project includes a custom recipe for:

```text
UxPlay 1.73.6
```

UxPlay provides AirPlay-compatible screen mirroring and audio streaming.

The recipe uses:

```text
Avahi
libplist
OpenSSL
GStreamer
```

It is configured for the headless Raspberry Pi environment with unnecessary X11 dependencies disabled.

---

# vSomeIP

The project integrates:

```text
COVESA vSomeIP 3.4.10
```

vSomeIP provides SOME/IP middleware for inter-process and network communication.

The recipe uses Boost and supports the two AMIT distributions.

For the systemd distribution, systemd integration is enabled when the `systemd` distro feature is present.

For the SysVinit distribution, vSomeIP can operate without the systemd dependency.

The recipe also corrects the upstream configuration installation location so configuration files are installed under:

```text
/etc/vsomeip/
```

Runtime libraries and development CMake files are packaged separately according to standard OpenEmbedded packaging conventions.

---

# External Yocto Layers

This repository contains the project-specific layers only.

The following external layers are required:

```text
poky
meta-raspberrypi
meta-openembedded/meta-oe
meta-qt5        # systemd distribution only
```

All layers should use branches compatible with:

```text
kirkstone
```

The original development environment used:

```text
Poky:
393064579dfdd0ed2d4ed4c27d238d7d2292c08b

meta-raspberrypi:
255500dd9f6a01a3445ac491d1abc401801e3bad

meta-openembedded:
ce8539c941f6fcbecaca4d16640ac105c0595589
```

---

# Preparing the Yocto Environment

Example directory layout:

```text
poky/
├── meta
├── meta-poky
├── meta-yocto-bsp
├── meta-raspberrypi
├── meta-openembedded/
│   └── meta-oe
├── meta-qt5
├── meta-amit
└── meta-observability
```

Clone the required Kirkstone layers before building.

Example:

```bash
git clone -b kirkstone https://git.yoctoproject.org/poky
git clone -b kirkstone https://git.yoctoproject.org/meta-raspberrypi
git clone -b kirkstone https://github.com/openembedded/meta-openembedded.git
git clone -b kirkstone https://github.com/meta-qt5/meta-qt5.git
```

Then place or clone this project's custom layers inside the Poky working directory.

---

# Build Distribution 1 — systemd + Qt5

Initialize a build environment:

```bash
source oe-init-build-env build-systemd
```

Set:

```bitbake
MACHINE = "raspberrypi4-64"
DISTRO = "amit-systemd"
```

Required layers include:

```text
meta
meta-poky
meta-yocto-bsp
meta-raspberrypi
meta-openembedded/meta-oe
meta-amit
meta-observability
meta-qt5
```

Build:

```bash
bitbake amit-systemd-image
```

---

# Build Distribution 2 — SysVinit

Initialize another build:

```bash
source oe-init-build-env build-sysvinit
```

Set:

```bitbake
MACHINE = "raspberrypi4-64"
DISTRO = "amit-sysvinit"
```

Required layers:

```text
meta
meta-poky
meta-yocto-bsp
meta-raspberrypi
meta-openembedded/meta-oe
meta-amit
meta-observability
```

`meta-qt5` must **not** be added to this build.

Build:

```bash
bitbake amit-sysvinit-image
```

---

# Build Output

Successful image files are generated under:

```text
tmp/deploy/images/raspberrypi4-64/
```

Typical output includes:

```text
*.wic
*.wic.bmap
*.manifest
*.tar.bz2
kernel images
device-tree blobs
```

These generated files are intentionally excluded from Git.

---

# Flashing the Raspberry Pi Image

The generated `.wic` image can be written to a microSD card using tools such as Raspberry Pi Imager, Balena Etcher, or `dd`.

Always confirm the target storage device before writing an image.

Example Linux command:

```bash
sudo dd if=<image>.wic of=/dev/<device> bs=4M status=progress conv=fsync
```

---

# Runtime Verification

After booting the Raspberry Pi, useful checks include:

```bash
uname -a
```

```bash
nano --version
```

```bash
ssh -V
```

```bash
hello-bullet
```

```bash
uxplay --help
```

Check vSomeIP libraries:

```bash
ls /usr/lib | grep vsomeip
```

Check observability:

```bash
cat /var/log/bullet-observability.log
```

For the systemd image:

```bash
systemctl status bullet-observer
```

For the SysVinit image:

```bash
/etc/init.d/bullet-observer status
```

---

# Development Environment

The project was developed inside Docker using a Kirkstone Yocto development image.

The build configuration used:

```text
Host build architecture: x86_64-linux
Target architecture: aarch64-poky-linux
Board: Raspberry Pi 4
CPU tuning: Cortex-A72
Package format: RPM
```

Shared download and sstate directories can be used between both build configurations to significantly reduce repeated compilation.

---

# Project Goals

The project demonstrates:

* Creating custom Yocto/OpenEmbedded layers
* Creating custom image recipes
* Creating custom distributions
* Using both systemd and SysVinit
* Cross-compiling a native ARM64 application
* Integrating community middleware
* Integrating Qt5
* Adding multimedia and AirPlay screen mirroring
* Adding Wi-Fi, SSH, audio, and development utilities
* Creating a lightweight embedded observability service
* Structuring reusable package groups
* Maintaining separate graphical and non-graphical Linux images

---

# License

Project-specific metadata and example applications are provided under the MIT License unless a recipe specifies the license of its corresponding upstream project.

Third-party software such as UxPlay, Qt, vSomeIP, GStreamer, Boost, and other OpenEmbedded packages retain their respective upstream licenses.

---

# Acknowledgements

This project uses technologies and metadata from:

* Yocto Project
* OpenEmbedded
* Raspberry Pi
* COVESA vSomeIP
* UxPlay
* Qt Project
* GStreamer
* AMIT / Bullet Embedded Linux project

