
(ns app.website-menu.backend.api
    (:require [app.website-menu.backend.editor.lifecycles]
              [app.website-menu.backend.editor.mutations]
              [app.website-menu.backend.editor.resolvers]
              [app.website-menu.backend.installer.side-effects]
              [app.website-menu.backend.handler.helpers :as handler.helpers]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; app.website-menu.backend.handler.helpers
(def get-website-menu handler.helpers/get-website-menu)
