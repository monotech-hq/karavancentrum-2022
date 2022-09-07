
(ns app.contents.backend.resolvers
    (:require [com.wsscode.pathom3.connect.operation :refer [defresolver]]
              [pathom.api                            :as pathom]
              [server-fruits.io                      :as io]
              [x.server-core.api                     :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defresolver get-config
             ; @param (map) env
             ; @param (map) resolver-props
             ;  {:sample/id (string)}
             ;
             ; @return (map)
             ;  {:sample/id 1
             ;   :sample/name "hello"}
             [env _]
             {:contents/get-config (io/read-edn-file)}) ;a/contents-FILEPATH)})

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @constant (vector)
(def HANDLERS [])

; (pathom/reg-handlers! ::handlers HANDLERS)
