
(ns app.settings.frontend.cookie-consent.subs
    (:require [re-frame.api          :refer [r]]
              [x.app-environment.api :as environment]
              [x.app-ui.api          :as ui]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn render-consent?
  [db _]
  (and      ;(r ui/application-interface?              db)
       (not (r environment/necessary-cookies-enabled? db))))
