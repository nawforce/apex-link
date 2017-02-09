#!/usr/bin/env python
import json
from sys import argv, stdin, stdout, stderr

filter_filename = argv[1]
with open(filter_filename, 'r') as filter_file:
    filters = set(json.load(filter_file))

lines_received = 0
lines_dropped = 0
lines_emitted = 0

for line in stdin:
    lines_received += 1
    parts = line.split("\t")
    if parts[0].strip() in filters:
        lines_dropped += 1
    else:
        lines_emitted += 1
        stdout.write(line)

stderr.write("{0}: {1} lines received\n".format(argv[0], lines_received))
stderr.write("{0}: {1} lines dropped\n".format(argv[0], lines_dropped))
stderr.write("{0}: {1} lines emitted\n".format(argv[0], lines_emitted))
