
(ns app.views.backend.api
    (:require [app.views.backend.error-screen.lifecycles]
              [app.views.backend.loading-screen.views :as loading-screen.views]
              [app.views.backend.menu-screen.lifecycles]
              [app.views.backend.privacy-policy.lifecycles]
              [app.views.backend.terms-of-service.lifecycles]))

;; -- Redirects ---------------------------------------------------------------
;; ----------------------------------------------------------------------------

; app.views.backend.loading-screen.views
(def loading-screen loading-screen.views/view)
