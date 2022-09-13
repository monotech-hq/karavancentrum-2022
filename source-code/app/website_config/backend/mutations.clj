
(ns app.website-config.backend.mutations
    (:require [com.wsscode.pathom3.connect.operation :as pathom.co :refer [defmutation]]
              [pathom.api                            :as pathom]
              [server-fruits.io                      :as io]
              [x.server-core.api                     :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defmutation save-changes!
             [env website-config]
             {::pathom.co/op-name 'website-config/save-changes!}
             (io/write-edn-file! a/WEBSITE-CONFIG-FILEPATH website-config)
             (a/dispatch-fx [:core/import-website-config!]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @constant (vector)
(def HANDLERS [save-changes!])

(pathom/reg-handlers! ::handlers HANDLERS)
