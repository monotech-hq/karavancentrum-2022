
(ns app.contents.frontend.selector.sample
    (:require [app.contents.frontend.api]
              [x.app-core.api :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :load-my-selector!
  [:contents.selector/load-selector! :my-selector {:value-path [:my-item]}])
