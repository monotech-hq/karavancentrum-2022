
(ns app.contents.frontend.handler.helpers
    (:require [app-fruits.html   :as html]
              [mid-fruits.string :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn parse-content-body
  ; @param (string) body
  ;
  ; @example
  ;  (contents/parse-content-body "<p>Paragraph #1</p><p>Paragraph #2</p>")
  ;  =>
  ;  [:div [:p "Paragraph #1"] [:p "Paragraph #2"]]
  ;
  ; @return (vector)
  [body]
  (if (string/nonempty? body)
      (html/to-hiccup (str "<div>"body"</div>"))))
