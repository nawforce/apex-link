
import {WorkspaceException, Workspaces} from '../pkgforce'

test('Bad directory', () => {
    try {
        Workspaces.get('foo');
        expect(true).toBe(false);
    } catch (err) {
        expect(err.message).toMatch(/^No directory at .*/)
    }
})

