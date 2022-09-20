
(ns routes
    (:require [app.website-config.backend.api :as website-config]
              [app.views.backend.api          :as views]
              [server-fruits.http             :as http]
              [x.server-core.api              :as a]
              [x.server-ui.api                :as ui])
    (:gen-class))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn view
  [request]
  (let [website-config (website-config/get-website-config)]
       (ui/html (ui/head request website-config)
                (ui/body request {:loading-screen (views/loading-screen request)}))))
               ;(ui/head request {:css-paths       [{:uri ""}]})
               ;(ui/body request {:plugin-js-paths [{:uri ""}]})

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(def METHOD-NOT-ALLOWED
  #(http/html-wrap {:body   (view %)
                    :status 404}))

(def NOT-ACCEPTABLE
  #(http/html-wrap {:body   (view %)
                    :status 404}))

(def NOT-FOUND
  #(http/html-wrap {:body   (view %)
                    :status 200}))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-lifecycles!
  ::lifecycles
  {:on-server-init {:dispatch-n [[:router/set-default-route! :method-not-allowed METHOD-NOT-ALLOWED]
                                 [:router/set-default-route! :not-acceptable     NOT-ACCEPTABLE]
                                 [:router/set-default-route! :not-found          NOT-FOUND]]}})
