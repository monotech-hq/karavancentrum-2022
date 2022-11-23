
(ns app.contents.backend.handler.helpers
    (:require [app.common.backend.api :as common]
              [candy.api              :refer [return]]
              [mongo-db.api           :as mongo-db]
              [x.user.api             :as x.user]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-content
  ; @param (map) env
  ;  {:request (map)}
  ; @param (namespaced map)
  ;  {:content/id (string)}
  ;
  ; @return (string)
  [{:keys [request]} {:content/keys [id]}]
  (let [projection (common/get-document-projection :content)]
       (if-let [{:content/keys [body visibility]} (mongo-db/get-document-by-id "contents" id projection)]
               (case visibility :private (if (x.user/request->authenticated? request)
                                             (return body))
                                :public      (return body)))))
