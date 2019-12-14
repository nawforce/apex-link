#!/usr/bin/env python
import json
from sys import argv, stdin, stdout, stderr
from collections import defaultdict
from key_functions import KeyParser

with open(argv[1], 'r') as key_patterns_file:
    key_patterns = json.load(key_patterns_file)

key_parser = KeyParser(key_patterns)

language_code = argv[2]

stf_preamble = (
"""Language code: {0}
Type: Bilingual

------------------TRANSLATED-------------------

""")

stf_preamble = stf_preamble.format(language_code)

translations_by_namespace = defaultdict(list)

for line in stdin:
    line = line.strip()
    parts = line.split("\t")
    try:
        namespace, parts[0] = key_parser.rewrite_key(parts[0])
        if not namespace:
            namespace = 'none'
        translations_by_namespace[namespace].append("\t".join(parts) + "\n")
    except LookupError as e:
        stderr.write("{0}: Warning: {1}\n".format(argv[0], e))

lines_written = 0

for namespace, translations in translations_by_namespace.items():
    with open(namespace + '_translations.stf', 'w') as out_file:
        stderr.write(
            "{0}: {1} lines written for {2}\n".format(
                argv[0],
                len(translations),
                namespace
            )
        )
        lines_written += len(translations)
        out_file.write(stf_preamble)
        out_file.writelines(translations)

stderr.write(
    "{0}: {1} total lines written\n".format(
        argv[0],
        lines_written
    )
)
