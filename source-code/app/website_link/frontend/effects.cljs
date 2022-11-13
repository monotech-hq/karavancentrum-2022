
(ns app.website-link.frontend.effects
  (:require [re-frame.api   :as r :refer [r]]
            [x.app-core.api :as x.core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(r/reg-event-fx :website-link/render-menu!
  [:components.context-menu/render-menu! :website-link/menu
                                         {:label :view-website!
                                          :menu-items [{:label :open!
                                                        :on-click [:website-link/open-website!]}
                                                       {:label :open-in-new-page!
                                                        :on-click [:website-link/open-website-in-new-page!]}]}])

(r/reg-event-fx :website-link/load-menu!
  [:website-link/render-menu!])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(r/reg-event-fx :website-link/open-website!
  (fn [{:keys [db]} _]
      (let [website-link (r x.core/get-app-config-item db :app-domain)]
           {:fx [:environment/go-to! website-link]})))

(r/reg-event-fx :website-link/open-website-in-new-page!
  (fn [{:keys [db]} _]
      (let [website-link (r x.core/get-app-config-item db :app-domain)]
           {:fx [:environment/open-tab! website-link]})))
