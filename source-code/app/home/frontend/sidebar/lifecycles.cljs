
(ns app.home.frontend.screen.lifecycles
    (:require [x.app-core.api :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(core/reg-lifecycles! ::lifecycles
  {:on-app-boot [:ui.sidebar/set-content! :home.sidebar/view
                                          {:core-js        "app.js"
                                           :route-template "/@app-home"
                                           :client-event   [:home.screen/load!]
                                           :restricted?    true}]})
