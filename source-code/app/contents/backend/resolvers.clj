
(ns app.contents.backend.resolvers
    (:require [com.wsscode.pathom3.connect.operation :refer [defresolver]]
              [pathom.api                            :as pathom]
              [server-fruits.io                      :as io]
              [x.server-core.api                     :as a]))

;; -----------------------------------------------------------------------------
;; ---- Configurations ----

(def FILE_PATH "monoset-environment/db/contents.edn")

;; ---- Configurations ----
;; -----------------------------------------------------------------------------


(defresolver get! [env _]
  {:contents/get! (io/read-edn-file FILE_PATH)})

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @constant (vector)
(def HANDLERS [get!])

(pathom/reg-handlers! ::handlers HANDLERS)
