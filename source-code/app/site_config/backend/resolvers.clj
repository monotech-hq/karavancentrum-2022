
(ns app.site-config.backend.resolvers
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
             {:site-config/get-config (io/read-edn-file a/SITE-CONFIG-FILEPATH)})

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @constant (vector)
(def HANDLERS [get-config])

(pathom/reg-handlers! ::handlers HANDLERS)
