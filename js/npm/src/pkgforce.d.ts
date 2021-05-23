export declare class WorkspaceException {
    message: string;
}

export declare class Workspace {
    findType(name: string): string | null;
}

export declare class Workspaces {
    static get(path: string): Workspace;
}

