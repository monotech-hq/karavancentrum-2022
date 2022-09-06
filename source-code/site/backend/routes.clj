
(ns site.backend.routes
  (:require [x.server-core.api :as a]))

(a/reg-lifecycles!
 ::lifecycles
 {:on-server-boot
  {:dispatch-n
   [
;; ---- Main page route ----
    [:router/add-route! :website/home-page
     {:core-js        "site.js"
      :route-template "/"
      :client-event   [:main-page/load!]}]]}})
  
