import re

class KeyParser(object):
    def __init__(self, key_patterns):
        self.key_patterns = key_patterns

    def namespace_split(self, name):
        """
            Given an API name, return a tuple containing the namespace prefix (if
            any) followed by the non-namespaced name.

            Examples:

            >>> parser.namespace_split('foo')
            (None, 'foo')

            >>> parser.namespace_split('foo__c')
            (None, 'foo__c')

            >>> parser.namespace_split('npsp__foo')
            ('npsp', 'foo')

            >>> parser.namespace_split('npsp__foo__c')
            ('npsp', 'foo__c')

            >>> parser.namespace_split('npsp__npsp__foo__c')
            ('npsp', 'foo__c')
        """
        name_parts = name.split('__')
        if len(name_parts) > 1 and name_parts[1] != 'c':
            namespace_prefix = name_parts[0]
        else:
            namespace_prefix = None

        if not namespace_prefix:
            return (None, name)

        name_without_namespace = re.sub(
            '^({0}__)+'.format(namespace_prefix),
            '',
            name
        )

        return (namespace_prefix, name_without_namespace)


    def rewrite_key_namespace(self, key, primary_index, secondary_indices):
        """Strip namespaces from a given key, and also determine which namespace
        the key belongs to.

        Params:

          key - The key to split
          primary_index - The index of the key part that determines which namespace this key belongs to
          secondary_indices - Indices of other key parts that will need to have namespaces stripped

        Examples:

        >>> parser.rewrite_key_namespace('KeyType.npsp__foo__c.npsp__bar', 2, [1])
        ('npsp', 'KeyType.foo__c.bar')

        >>> parser.rewrite_key_namespace('KeyType.npe01__foo__c.npsp__bar', 2, [1])
        ('npsp', 'KeyType.npe01__foo__c.bar')

        >>> parser.rewrite_key_namespace('KeyType.foo__c.npsp__bar', 2, [1])
        ('npsp', 'KeyType.foo__c.bar')
        """
        key_parts = key.split('.')
        primary_namespace, primary_name = self.namespace_split(key_parts[primary_index])
        key_parts[primary_index] = primary_name

        if not secondary_indices:
            secondary_indices = []

        for secondary_index in secondary_indices:
            secondary_namespace, secondary_name = self.namespace_split(key_parts[secondary_index])
            if secondary_namespace == primary_namespace:
                key_parts[secondary_index] = secondary_name

        return (primary_namespace, '.'.join(key_parts))

    def get_indices_by_key_type(self, key):
        """Given a translation key, return the known indices of that key for
        namespace processing.

        Params:

            key - The key to determine type of

        Examples:

        >>> parser.get_indices_by_key_type('ButtonOrLink.Opportunity.npsp__Email_Acknowledgement')
        ('ButtonOrLink.Opportunity.npsp__Email_Acknowledgement', 2, [1])

        >>> parser.get_indices_by_key_type('CrtLayoutSection.npsp__Contacts_Only_1')
        ('CrtLayoutSection.npsp__Contacts_Only_1', 1, None)

        >>> parser.get_indices_by_key_type('CustomField.Opportunity.npsp__Tribute_Type.FieldLabel')
        ('CustomField.Opportunity.npsp__Tribute_Type.FieldLabel', 2, [1])

        >>> parser.get_indices_by_key_type('UnknownKeyType.Foo.npsp__Bar.Baz')
        Traceback (most recent call last):
            ...
        LookupError: Could not identify the type of key for: UnknownKeyType.Foo.npsp__Bar.Baz
        """
        for pattern, indices in self.key_patterns.items():
            if re.match(pattern, key):
                return (key, indices['primary_index'], indices['secondary_indices'])

        raise LookupError('Could not identify the type of key for: ' + key)

    def rewrite_key(self, key):
        """Strip namespaces from a given key, and also determine which namespace
        the key belongs to.

        Params:

          key - The key to split

        Examples:

        >>> parser.rewrite_key('ButtonOrLink.Opportunity.npsp__Email_Acknowledgement')
        ('npsp', 'ButtonOrLink.Opportunity.Email_Acknowledgement')

        >>> parser.rewrite_key('CrtLayoutSection.npsp__Contacts_Only_1')
        ('npsp', 'CrtLayoutSection.Contacts_Only_1')

        >>> parser.rewrite_key('CustomField.Opportunity.npsp__Tribute_Type.FieldLabel')
        ('npsp', 'CustomField.Opportunity.Tribute_Type.FieldLabel')

        >>> parser.rewrite_key('UnknownKeyType.Foo.npsp__Bar.Baz')
        Traceback (most recent call last):
            ...
        LookupError: Could not identify the type of key for: UnknownKeyType.Foo.npsp__Bar.Baz
        """
        return self.rewrite_key_namespace(*self.get_indices_by_key_type(key))

if __name__ == '__main__':
    import doctest
    test_key_patterns = {
        "^ButtonOrLink\.[^.]+\.[^.]+$": {
            "primary_index": 2,
            "secondary_indices": [1]
        },
        "^CrtLayoutSection\.[^.]+$": {
            "primary_index": 1,
            "secondary_indices": None
        },
        "^CustomField\.[^.]+\.[^.]+\.(FieldLabel|HelpText|RelatedListLabel)$": {
            "primary_index": 2,
            "secondary_indices": [1]
        }
    }
    doctest.testmod(
        extraglobs={
            'parser': KeyParser(test_key_patterns)
        }
    )
