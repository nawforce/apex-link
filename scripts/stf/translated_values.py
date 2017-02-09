#!/usr/bin/env python
from sys import argv, stdin, stdout, stderr

TRANSLATED_MARKER = '------------------TRANSLATED-------------------'
UNTRANSLATED_MARKER = '------------------UNTRANSLATED-----------------'

lines_emitted = 0
in_translated_section = False

for line in stdin:
    sline = line.strip()
    if not sline:
        # ignore empty lines
        pass
    elif sline.startswith('#'):
        # ignore comments
        pass
    elif not in_translated_section and sline == TRANSLATED_MARKER:
        in_translated_section = True
        pass
    elif in_translated_section and sline == UNTRANSLATED_MARKER:
        in_translated_section = False
        pass
    elif in_translated_section:
        lines_emitted += 1
        stdout.write(line)
        pass

stderr.write(
    "{0}: {1} lines emitted\n".format(
        argv[0],
        lines_emitted
    )
)
