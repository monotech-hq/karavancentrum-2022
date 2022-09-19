
(ns backend
    (:require ; shadow-cljs
              [shadow.cljs.devtools.server :as server]
              [shadow.cljs.devtools.api    :as shadow]

              ; *
              [routes]

              ; App modules
              [app.common.backend.api]
              [app.home.backend.api]
              [app.settings.backend.api]
              [app.website-config.backend.api]
              [app.storage.backend.api]
              [app.user.backend.api]
              [app.vehicles.backend.api]
              [app.views.backend.api]
              [app.contents.backend.api]

              ; Site modules
              [site.pages.backend]

              ; monoset/x
              [x.boot-loader.api :as boot-loader]
              [x.server-core.api :as a])
    (:gen-class))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn start-server!
  [{:keys [port] :as server-props}]
  (boot-loader/start-server! server-props)
  (println "project-emulator - Server started on port:" port))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn -main
  ; @param (list of *) args
  ;  [(integer)(opt) port]
  ;
  ; @usage
  ;  (-main 3000)
  ;
  ; @usage
  ;  java -jar {{namespace}}.jar 3000
  [& [port :as args]]
  (start-server! {:port port}))

(defn dev
  ; @param (map) options
  ;  {:port (integer)
  ;   :shadow-build (keyword)}
  ;
  ; @usage
  ;  (dev {:shadow-build :my-build})
  [{:keys [port shadow-build]}]
  (start-server! {:port port :dev-mode? true})
  (server/stop!)
  (server/start!)
  (shadow/watch shadow-build)
  (println "project-emulator - Development mode started"))
