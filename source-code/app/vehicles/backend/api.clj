
(ns app.vehicles.backend.api
  (:require
    [app.vehicles.backend.editor.mutations]
    [app.vehicles.backend.editor.resolvers]
    [app.vehicles.backend.editor.lifecycles]
    [app.vehicles.backend.lister.mutations]
    [app.vehicles.backend.lister.resolvers]
    [app.vehicles.backend.lister.lifecycles]
    [app.vehicles.backend.viewer.mutations]
    [app.vehicles.backend.viewer.resolvers]
    [app.vehicles.backend.viewer.lifecycles]
    [forms.api]
    [layouts.popup-a.api]
    [layouts.surface-a.api]))
