
(ns app.site-config.backend.mutations
    (:require [com.wsscode.pathom3.connect.operation :as pathom.co :refer [defmutation]]
              [pathom.api                            :as pathom]
              [server-fruits.io                      :as io]
              [x.server-core.api                     :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defmutation save-config!
             [env site-config]
             {::pathom.co/op-name 'site-config/save-config!}
             (io/write-edn-file! a/SITE-CONFIG-FILEPATH site-config))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @constant (vector)
(def HANDLERS [save-config!])

(pathom/reg-handlers! ::handlers HANDLERS)
