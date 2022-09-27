
(ns app.common.backend.api
    (:require [layouts.popup-a.api]
              [layouts.popup-b.api]
              [layouts.surface-a.api]
              [app.common.backend.prototypes :as prototypes]))

;; -- Redirects ---------------------------------------------------------------
;; ----------------------------------------------------------------------------

; app.common.backend.prototypes
(def added-document-prototype      prototypes/added-document-prototype)
(def updated-document-prototype    prototypes/updated-document-prototype)
(def duplicated-document-prototype prototypes/duplicated-document-prototype)
