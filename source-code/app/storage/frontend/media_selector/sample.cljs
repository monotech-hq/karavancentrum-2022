
(ns app.storage.frontend.media-selector.sample
    (:require [app.storage.frontend.api]
              [x.app-core.api :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :load-my-media-selector!
  [:storage.media-selector/load-selector! :my-selector {:value-path [:my-item]}])
