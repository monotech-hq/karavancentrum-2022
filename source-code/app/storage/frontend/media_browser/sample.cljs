
(ns app.storage.frontend.media-browser.sample
    (:require [app.storage.frontend.api]
              [x.app-core.api :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx :load-my-media-browser!
  [:router/go-to! "/@app-home/storage"])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx :load-your-media-browser!
  [:router/go-to! "/@app-home/storage/my-directory"])
