
(ns app.contents.backend.mutations
    (:require [com.wsscode.pathom3.connect.operation :as pathom.co :refer [defmutation]]
              [pathom.api                            :as pathom]
              [server-fruits.io                      :as io]
              [x.server-core.api                     :as a]))

;; -----------------------------------------------------------------------------
;; ---- Configurations ----

(def FILE_PATH "monoset-environment/db/contents.edn")

;; ---- Configurations ----
;; -----------------------------------------------------------------------------
(defmutation save! [env contents]
  {::pathom.co/op-name 'contents/save!}
  (io/write-edn-file! FILE_PATH contents))
  ;(a/dispatch-fx [:core/import-website-config!]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @constant (vector)
(def HANDLERS [save!])

(pathom/reg-handlers! ::handlers HANDLERS)
