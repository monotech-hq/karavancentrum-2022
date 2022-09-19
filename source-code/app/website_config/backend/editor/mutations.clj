
(ns app.website-config.backend.editor.mutations
    (:require [com.wsscode.pathom3.connect.operation :as pathom.co :refer [defmutation]]
              [mid-fruits.candy                      :refer [return]]
              [pathom.api                            :as pathom]
              [server-fruits.io                      :as io]
              [x.server-core.api                     :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn save-config-f
  ; @param (map) env
  ; @param (map) mutation-props
  ;  {:config (map)}
  ;
  ; @return (map)
  [env {:keys [config]}]
  (io/write-edn-file! a/WEBSITE-CONFIG-FILEPATH config)
  (a/dispatch-fx [:core/import-website-config!])
  (return config))

(defmutation save-config!
             ; @param (map) env
             ; @param (map) mutation-props
             ;  {:config (map)}
             ;
             ; @return (map)
             [env mutation-props]
             {::pathom.co/op-name 'website-config/save-config!}
             (save-config-f env mutation-props))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @constant (functions in vector)
(def HANDLERS [save-config!])

(pathom/reg-handlers! ::handlers HANDLERS)
