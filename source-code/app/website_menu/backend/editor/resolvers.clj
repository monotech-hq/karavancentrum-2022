
(ns app.website-menu.backend.editor.resolvers
    (:require [app.website-menu.backend.handler.config :as handler.config]
              [com.wsscode.pathom3.connect.operation   :refer [defresolver]]
              [io.api                                  :as io]
              [pathom.api                              :as pathom]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- get-content-f
  ; @param (map) env
  ; @param (map) resolver-props
  ;
  ; @return (map)
  [_ _]
  (io/read-edn-file handler.config/WEBSITE-MENU-FILEPATH))

(defresolver get-content
             ; @param (map) env
             ; @param (map) resolver-props
             ;
             ; @return (namespaced map)
             ;  {:website-menu.editor/get-content (map)}
             [env resolver-props]
             {:website-menu.editor/get-content (get-content-f env resolver-props)})

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @constant (functions in vector)
(def HANDLERS [get-content])

(pathom/reg-handlers! ::handlers HANDLERS)
