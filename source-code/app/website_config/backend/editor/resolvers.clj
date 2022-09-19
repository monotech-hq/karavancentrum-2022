
(ns app.website-config.backend.editor.resolvers
    (:require [com.wsscode.pathom3.connect.operation :refer [defresolver]]
              [pathom.api                            :as pathom]
              [server-fruits.io                      :as io]
              [x.server-core.api                     :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- get-config-f
  ; @param (map) env
  ; @param (map) resolver-props
  ;
  ; @return (map)
  [env resolver-props]
  (io/read-edn-file a/WEBSITE-CONFIG-FILEPATH))

(defresolver get-config
             ; @param (map) env
             ; @param (map) resolver-props
             ;
             ; @return (map)
             ;  {:website-config/get-config (map)}
             [env resolver-props]
             {:website-config/get-config (get-config-f env resolver-props)})

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @constant (functions in vector)
(def HANDLERS [get-config])

(pathom/reg-handlers! ::handlers HANDLERS)
