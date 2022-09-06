
(ns app.views.frontend.no-internet-notification.lifecycles
    (:require [x.app-core.api :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-lifecycles!
  ::lifecycles
  {:on-browser-offline [:views.no-internet-notification/blow-no-internet-bubble?!]
   :on-app-launch      [:views.no-internet-notification/blow-no-internet-bubble?!]
   :on-browser-online  [:ui/close-bubble! :views.no-internet-notification/notification]})
