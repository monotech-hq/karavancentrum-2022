
(ns app.contents.frontend.handler.helpers
    (:require [html.api   :as html]
              [string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn parse-content-body
  ; @param (string) body
  ;
  ; @example
  ;  (parse-content-body "<p>Paragraph #1</p><p>Paragraph #2</p>")
  ;  =>
  ;  [:div [:p "Paragraph #1"] [:p "Paragraph #2"]]
  ;
  ; @return (vector)
  [body]
  ; Az html.api/to-hiccup függvény az átadott html string első html elemét
  ; konvertálja hiccup típusra, ezért szükséges a tartalmat egy div elembe helyezni,
  ; hogy az egész tartalom az első elemben legyen!
  (if (string/nonblank? body)
      (html/to-hiccup (str "<div>"body"</div>"))))

(defn nonblank?
  ; @param (string) body
  ;
  ; @example
  ;  (nonblank? "<p>Paragraph #1</p><p>Paragraph #2</p>")
  ;  =>
  ;  true
  ;
  ; @example
  ;  (nonblank? "<p> </p><p>\n</p>")
  ;  =>
  ;  false
  ;
  ; @return (boolean)
  [body]
  (-> body (string/remove-part #"<[a-z]{1,}>")
           (string/remove-part #"</[a-z]{1,}>")
           (string/remove-part #"<[a-z]{1,}/>")
           (string/remove-part #" ")
           (string/remove-part #"\r")
           (string/remove-part #"\n")
           (empty?)))
