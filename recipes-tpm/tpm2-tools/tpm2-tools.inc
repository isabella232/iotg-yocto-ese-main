SUMMARY = "Tools for TPM2"
DESCRIPTION = "This project builds a set of command-line tools for \
interacting with a TPM (Trusted Platform Module) 2."
HOMEPAGE = "https://github.com/tpm2-software/tpm2-tools"
SECTION = "security/tpm"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=0eb1216e46938bd723098d93a23c3bcc"

DEPENDS = " \
    tpm2-abrmd openssl curl autoconf-archive-native \
    tpm2-tss libtss2 libtss2-mu libtss2-tcti-device libtss2-tcti-mssim \
    "

inherit autotools pkgconfig bash-completion