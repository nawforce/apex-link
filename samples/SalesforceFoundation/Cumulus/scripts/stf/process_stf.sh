#!/usr/bin/env bash

cat "$1" | ./translated_values.py | ./filter.py filters.json | ./sort_translations.py key_patterns.json $2
