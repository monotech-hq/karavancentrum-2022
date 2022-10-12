
(ns app.settings.frontend.privacy.views
    (:require [settings.cookie-settings.views :rename {body cookie-settings}]
              [x.app-elements.api             :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn body
  [_]
  [cookie-settings])
