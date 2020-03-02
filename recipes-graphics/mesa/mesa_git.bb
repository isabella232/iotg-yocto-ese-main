require ${COREBASE}/meta/recipes-graphics/mesa/mesa.inc
inherit manpages update-alternatives

LIC_FILES_CHKSUM = "file://docs/license.html;md5=c1843d93c460bbf778d6037ce324f9f7"
mesa_url ?= "git://gitlab.freedesktop.org/mesa/mesa;branch=master;protocol=https"
## Upstream free-destkop mesa master Mon Jun 10 14:23:34 2019 -0700
mesa_srcrev ?= "ae7bda27a0691d6d89c35c9f732b6e49d726c17f"
mesa_pv ?= "20.1.0+git${SRCPV}"

SRC_URI = "${mesa_url}"
PV = "${mesa_pv}"
SRCREV = "${mesa_srcrev}"

SRC_URI_append = " \
               file://0001-Revert-egl-fix-_EGL_NATIVE_PLATFORM-fallback.patch \
"

S = "${WORKDIR}/git"

# This mesa 18.3.0 related fix should be added in the yocto default recipe meta layer.
# Remove following once the upstream fix is available in the future
FILES_${PN} += "${datadir}/drirc.d/00-mesa-defaults.conf"

#because we cannot rely on the fact that all apps will use pkgconfig,
#make eglplatform.h independent of MESA_EGL_NO_X11_HEADER
do_install_append() {
    if ${@bb.utils.contains('PACKAGECONFIG', 'egl', 'true', 'false', d)}; then
        sed -i -e 's/^#if defined(MESA_EGL_NO_X11_HEADERS)$/#if defined(MESA_EGL_NO_X11_HEADERS) || ${@bb.utils.contains('PACKAGECONFIG', 'x11', '0', '1', d)}/' ${D}${includedir}/EGL/eglplatform.h
    fi
}


# mesa driver settings (should be in mesa-megadriver)
do_install_append() {
	install -m 755 -d ${D}${sysconfdir}/profile.d
	if [ -n "${MESA_FORCE_DRIVER}" ]; then
		echo 'export MESA_LOADER_DRIVER_OVERRIDE=${MESA_FORCE_DRIVER}' > ${D}${sysconfdir}/profile.d/mesa_driver.sh
	else
		: > ${D}${sysconfdir}/profile.d/mesa_driver.sh
	fi
}
