
(ns app.website-impressum.backend.api
    (:require [app.website-impressum.backend.editor.lifecycles]
              [app.website-impressum.backend.editor.mutations]
              [app.website-impressum.backend.editor.resolvers]
              [app.website-impressum.backend.handler.helpers :as handler.helpers]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; app.website-impressum.backend.handler.helpers
(def get-website-impressum handler.helpers/get-website-impressum)
