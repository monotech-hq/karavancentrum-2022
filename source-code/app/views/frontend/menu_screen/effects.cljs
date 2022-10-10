
(ns app.views.frontend.menu-screen.effects
    (:require [app.views.frontend.menu-screen.views :as menu-screen.views]
              [x.app-core.api                       :as a :refer [r]]
              [x.app-gestures.api                   :as gestures]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx :views.menu-screen/render!
  (fn [{:keys [db]} _]
      {:db       (r gestures/init-view-handler! db :views.menu-screen/handler {:default-view-id :main})
       :dispatch [:ui/render-popup! :views.menu-screen/view
                                    {:content #'menu-screen.views/view}]}))
