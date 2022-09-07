
(ns app.contents.backend.mutations
    (:require [com.wsscode.pathom3.connect.operation :as pathom.co :refer [defmutation]]
              [pathom.api                            :as pathom]
              [server-fruits.io                      :as io]
              [x.server-core.api                     :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defmutation save-config!
             [env contents]
             {::pathom.co/op-name 'contents/save-config!}
             (io/write-edn-file! ""));a/contents-FILEPATH contents))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @constant (vector)
(def HANDLERS [])

; (pathom/reg-handlers! ::handlers HANDLERS)
