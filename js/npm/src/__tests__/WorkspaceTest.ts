import {Workspaces} from "../pkgforce";
import {vol} from "memfs";
import {patchFs} from "fs-monkey";

test("Bad directory", () => {
    try {
        Workspaces.get("foo");
        expect(true).toBe(false);
    } catch (err) {
        expect(err.message).toMatch(/.*No directory at .*/);
    }
});

test("Empty directory", () => {
    vol.fromJSON({"/pkg1/README.md": ""});
    const unpatch = patchFs(vol);
    try {
        const workspace = Workspaces.get("/pkg1");
        expect(workspace).toBeDefined();
        expect(workspace.findType("Foo")).toBeNull;
    } finally {
        unpatch();
    }
});

test("MDAPI Class", () => {
    vol.fromJSON({"/pkg2/classes/Foo.cls": ""});
    const unpatch = patchFs(vol);
    try {
        const workspace = Workspaces.get("/pkg2");
        expect(workspace).toBeDefined();
        expect(workspace.findType("Foo")).toEqual(["/pkg2/classes/Foo.cls"]);
    } finally {
        unpatch();
    }
});

test("SFDX Bad Project file", () => {
    vol.fromJSON({"/pkg3/sfdx-project.json": ""});
    const unpatch = patchFs(vol);
    try {
        const workspace = Workspaces.get("/pkg3");
        expect(true).toBe(false);
    } catch (err) {
        expect(err.message).toMatch(
            "/pkg3/sfdx-project.json line 1 Error Failed to parse - ujson.IncompleteParseException: exhausted input"
        );
    } finally {
        unpatch();
    }
});

test("SFDX without namespace", () => {
    vol.fromJSON({
        "/pkg4/sfdx-project.json": '{ "packageDirectories": [{"path": "classes"}]}',
        "/pkg4/classes/Foo.cls": "",
    });
    const unpatch = patchFs(vol);
    try {
        const workspace = Workspaces.get("/pkg4");
        expect(workspace).toBeDefined();
        expect(workspace.findType("Foo")).toEqual(["/pkg4/classes/Foo.cls"]);
    } finally {
        unpatch();
    }
});

test("SFDX with namespace", () => {
    vol.fromJSON({
        "/pkg5/sfdx-project.json": '{ "packageDirectories": [{"path": "classes"}], "namespace": "ns001"}',
        "/pkg5/classes/Foo.cls": "",
    });
    const unpatch = patchFs(vol);
    try {
        const workspace = Workspaces.get("/pkg5");
        expect(workspace).toBeDefined();
        expect(workspace.findType("ns001.Foo")).toEqual(["/pkg5/classes/Foo.cls"]);
    } finally {
        unpatch();
    }
});
