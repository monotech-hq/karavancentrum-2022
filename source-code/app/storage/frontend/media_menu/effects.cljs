
(ns app.storage.frontend.media-menu.effects
    (:require [app.storage.frontend.media-menu.views :as media-menu.views]
              [x.app-core.api                        :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :storage.media-browser/render-directory-menu!
  ; WARNING! NON-PUBLIC! DO NOT USE!
  (fn [{:keys [db]} [_ directory-item]]
      [:ui/render-popup! :storage.media-menu/view
                         {:content [media-menu.views/directory-menu directory-item]}]))

(a/reg-event-fx
  :storage.media-browser/render-file-menu!
  ; WARNING! NON-PUBLIC! DO NOT USE!
  (fn [{:keys [db]} [_ file-item]]
      [:ui/render-popup! :storage.media-menu/view
                         {:content [media-menu.views/file-menu file-item]}]))
